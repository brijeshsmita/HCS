import {Component, OnInit} from '@angular/core';
import { HcsService } from '../_service/app.hcsservice';
import { CenterModel } from '../_model/app.centermodel';
import { TestModel } from '../_model/app.testmodel';

@Component({
    selector: 'deletetest',
    templateUrl:'../_html/app.deleteTest.html'
})

export class DeleteTestComponent implements OnInit{
    centerList:CenterModel[]=[];
    centerId:any;
    testList:TestModel[]=null;
    testId:any;

    constructor(private service:HcsService){    
    }

    ngOnInit()  {
        this.centerId=null;
        this.testList=null;
        this.testId=null;
        this.service.getCenters().subscribe((centerList:CenterModel[]) => this.centerList = centerList);
    }

    selectCenter(center:CenterModel){
        if(this.centerId == null)
        {
            this.centerId=center.centerId;
            this.centerList=[];
            this.centerList.push(center);
            this.service.getTests(center.centerId).subscribe((testList:TestModel[]) => this.testList = testList);
        }   
    }

    changeCenter(){
        this.ngOnInit();
    }

    delete(test:TestModel){
        this.testId=test.testId;
        this.testList = [];
        this.testList.push(test);
    }

    confirmDelete(){
        this.service.deleteTest(this.centerId,this.testId).subscribe(
         (data:boolean) => alert("Deleted Successfully.")
         ,error => alert("Please refresh the page.")
        );
        this.testId=null;
        this.testList = [];
        this.service.getTests(this.centerId).subscribe((testList:TestModel[]) => this.testList = testList);

    }

}