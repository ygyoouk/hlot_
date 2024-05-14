<template>
  <ModalLayout>
    <div class="modal-title">
      계약 관리{{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">

      <v-container>

          <v-row>
            <v-col>
              <v-file-input label="계약파일" @change="selectFile" ></v-file-input>
            </v-col>
            <!-- <v-col>
              <a :href="'http://localhost:8081/common/download/' + this.topContr.fileId">{{this.topContr.orignFileName}}</a>
            </v-col> -->
          </v-row>

          <v-row>
            <v-col>
              <v-select
              label="원계약"
              :items="topContr"
              item-title="topContrNm"
              item-value="topContrId"
              :readonly="mode === 'D'"
              >
              </v-select>
            </v-col>
             <v-col>
              <v-select
              label="업체"
              :items="company"
              item-title="compName"
              item-value="compId"
              :readonly="mode === 'D'"
              >
              </v-select>
            </v-col>
          </v-row>

          <v-row>
           <v-col>
              <v-text-field
              label="계약명"
              :readonly="mode === 'D'"
              v-model="contr.topCOntrId">
              </v-text-field>
            </v-col>
             <v-col>
              <v-text-field
              label="계약금액"
              :readonly="mode === 'D'"
              v-model="contr.contrAmount">
              </v-text-field>
            </v-col>
          </v-row>
          <v-row >
            <v-col>
              <v-text-field
              label="지불조건"
              :readonly="mode === 'D'"
              v-model="contr.paymentTerm">
              </v-text-field>
            </v-col>
             
            <v-col>
              <v-text-field
              label="계약금액"
              :readonly="mode === 'D'"
              v-model="contr.paymentTerm">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <label>계약일자</label> 
              <br/>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="contr.contrStDate"/> ~  
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="contr.contrEndDate"/>
               
              <v-textarea label="특이사항" variant="outlined" rows="10" v-model="contr.specialNote"></v-textarea>
            </v-col>
           
          </v-row>
          <v-row>
             <div class="modal-btn-list">
                <v-btn
                  v-if="this.mode == 'R'"
                  color="blue"
                  @click="saveContrFile"
                >파일 저장</v-btn>

                <v-btn
                  v-if="this.mode == 'R'"
                  color="green"
                  @click="newProject"
                >확정</v-btn>
            </div>
          </v-row>
      </v-container>

    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
</script>

<script>
import store from "@/store/store";
import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_PORT = import.meta.env.VITE_BE_PORT;
import {MODAL_MODE} from "@/util/config";
import utils from "@/util/validUtil";
import contrApi from '@/api/contr';
import companyApi from '@/api/company';
import topContrApi from '@/api/project';


export default {
  name: "ProjectModal",

  beforeMount(){
    //상세조회
    if(this.mode == 'D'){
        /** 계약 단건 조회*/
        this.contr();
    }else{
      console.log("@@@@@@@@@@@");
      this.getSelectBox();
    }

  },

  mounted(){
    console.log(this.topContr.fileId);
    if(this.mode == 'D'){

    }

    if(this.topContr.topContrNm !== ''){
      this.visible = true;
    }
  },

  data() {
    return {
      visible : false,

      mode: store.getters.getParams.mode,

      key:store.getters.getParams.key,

      topContr : [],  

      company : [],

      contr : {
        topContrNm : '',
        topContrId : '',
        compId : '',
        companyNm : '',
        contrNm : '',
        contrAmount : '',
        paymentTerm : '',
        specialNote : '',
        contrStDate : '',
        contrEndDate : '',
      },

      file : '',
    }
  },

  methods: {
      close:()=>{
        store.commit("toggleModal");
      },

      // 프로젝트 정보 등록
      async newProject(){

        const formData = new FormData();
        formData.append('file' ,this.image);


        this.topContr.contrStDate = utils.saveDate(this.topContr.contrStDate);
        this.topContr.contrEndDate = utils.saveDate(this.topContr.contrEndDate);
        this.topContr.topContrDate = utils.saveDate(this.topContr.topContrDate);
        this.topContr.deliveryDeadline = utils.saveDate(this.topContr.deliveryDeadline);

        const blob = new Blob([JSON.stringify(this.topContr)],{type:'application/json'});

        formData.append('data' ,blob);

        await projectApi.newProject(formData);
        
        this.close();
      },

      // 계약 단건조회
      async contr(){
        this.topContr = await projectApi.project(this.key);
      },

      
      /**
       * select box (원계약,업체) 목록조회
      */
      async getSelectBox(){
        
        // 원계약
        this.topContr = await topContrApi.projects();
        // 업체
        this.company = await companyApi.companys();

      },


      // 모드 변경
      updateMode(){
          this.mode = 'M';
      },

      selectFile(file){
        console.log(file.target.files[0]);
        this.file = file.target.files[0];
      },

      /**
       * 계약 파일 저장
       * 
      */
      async saveContrFile(){

        const formData = new FormData();
        formData.append('file' ,this.file);

        const res= await contrApi.saveContrFile(formData);

        console.log(res);
      }


    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
