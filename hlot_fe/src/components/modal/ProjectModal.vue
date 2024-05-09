<template>
  <ModalLayout>
    <div class="modal-title">
      원계약 관리{{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">

      <v-container>

          <v-row>
            <v-col>
              <v-text-field
                v-model="topContr.topContrNm"
                label="원계약명"
                :readonly="mode === 'D'"
              ></v-text-field>
            </v-col>
            <v-col>
              <label> 원계약 기간</label><br>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="topContr.contrStDate"/>  ~
              <input type="date" id="endDate" :readonly="mode === 'D'" v-model="topContr.contrEndDate"/>
            </v-col>
          </v-row>
          <v-row>
           <v-col>
             <v-select
            label="구분"
            :items="['유지관리', '구축', '개발']"
            v-model="topContr.topContrDiv"
            :readonly="mode === 'D'"
            >
            </v-select>
           </v-col>
           <v-col>
              <v-text-field
              label="발주처"
              :readonly="mode === 'D'"
              v-model="topContr.clientComp">
              </v-text-field>
            </v-col>
          </v-row>
          <v-row >
            <v-col>
              <label> 원계약일자</label><br>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="topContr.topContrDate"/>
            </v-col>
             <v-col>
              <v-text-field
              label="품명"
              :readonly="mode === 'D'"
              v-model="topContr.prodNm">
              </v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
              label="계약금액"
              :readonly="mode === 'D'"
              v-model="topContr.contrAmount">
              </v-text-field>
            </v-col>
            <v-col>
              <label>납품기한</label><br>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="topContr.deliveryDeadline"/>
            </v-col>
          </v-row>
          <v-row>
          <v-col>
              <v-text-field
              label="수요기관명"
              :readonly="mode === 'D'"
              v-model="topContr.demandInstNm">
              </v-text-field>
            </v-col>
            <v-col v-if="this.topContr.fileId == '' ">
              <v-file-input label="원계약파일" @change="selectFile" ></v-file-input>
            </v-col>
            <v-col v-if="this.topContr.fileId != '' && this.topContr.fileId !=null">
              <a :href="'http://localhost:8081/common/download/' + this.topContr.fileId">{{this.topContr.orignFileName}}</a>
            </v-col>
          </v-row>
          <v-row>
             <div class="modal-btn-list">
                <v-btn
                  v-if="this.mode == 'R'"
                  color="green"
                  @click="newProject"
                >저장</v-btn>
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
import projectApi from '@/api/project.js'


export default {
  name: "ProjectModal",

  beforeMount(){
    //상세조회
    if(this.mode == 'D'){
        /** 프로젝트 단건 조회*/
        this.getProject();
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


      topContr : {
        topContrId : '',
        topContrNm : '',  // 원계약명
        topContrDiv : '', // 원계약구분
        clientComp : '', // 발주처
        topContrDate : '', // 원계약일자
        contrStDate : '', // 계약시작일자
        contrEndDate : '', // 계약종료일자
        prodNm : '', // 품명
        contrAmount : '', // 계약금액
        deliveryDeadline : '', // 납품기한
        demandInstNm : '', // 수요기관명
        topContrFileId : '',  // 첨부파일ID
        fileId : '', // 파일ID
        filePath : '', // 파일 경로
        orignFileName : '', // 파일 원본명
      },
      image : '',
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

      // 프로젝트 단건조회
      async getProject(){
        this.topContr = await projectApi.project(this.key);

        console.log(JSON.stringify(this.topContr));
        this.topContr.contrStDate = utils.formatDate(this.topContr.contrStDate); // 원계약시작일자
        this.topContr.contrEndDate = utils.formatDate(this.topContr.contrEndDate); // 원계약종료일자
        this.topContr.topContrDate = utils.formatDate(this.topContr.topContrDate);  // 원계약일자
        this.topContr.deliveryDeadline = utils.formatDate(this.topContr.deliveryDeadline); // 납품기한

      },


      // 모드 변경
      updateMode(){
          this.mode = 'M';
      },

      selectFile(file){
        console.log(file.target.files[0]);
        this.image = file.target.files[0];
      }


    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
