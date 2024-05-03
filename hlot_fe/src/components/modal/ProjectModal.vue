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
            <v-col>
              <v-file-input label="원계약파일" @change="selectFile"></v-file-input>
            </v-col>
          </v-row>
          <v-row>
            <!-- <v-btn id="regist" v-if="mode == 'R'" @click="registProject">등록</v-btn>
            <v-btn id="update" v-if="mode == 'D' || mode == 'M'" @click="updateProject">수정</v-btn>
            <v-btn @click="close()">취소</v-btn> -->
            
             <div class="modal-btn-list">
                <v-btn
                  color="blue"
                  @click="updateMode"
                  v-if="mode === 'D'"
                >수정</v-btn>
                　
                <v-btn
                  color="green"
                  @click="newProject"
                >저장</v-btn>
                　
                <v-btn
                  color="red"
                >삭제</v-btn>
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
    
    if(this.mode == 'D'){
      
    }
    
    if(this.project.projectName !== ''){
      this.visible = true;
    }
  },  

  data() {
    return {
      visible : false,

      mode: store.getters.getParams.mode,

      key:store.getters.getParams.key,

      topContr : {
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
        formData.append('image',this.image);
        
        await projectApi.newProject(this.project,formData);
        this.close();
      },

      // 프로젝트 단건조회
      async getProject(){ 
        
         this.project = await projectApi.project(this.key);
      },

      // 모드 변경
      updateMode(){
          this.mode = 'M';
      },

      selectFile(file){
        console.log("!!!!!!!!!!!!");
        console.log(file);
        this.image = file;
      }
     

    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
