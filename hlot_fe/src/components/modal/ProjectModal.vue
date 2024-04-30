<template>
  <ModalLayout>
    <div class="modal-title">
      프로젝트 관리{{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">
      <v-container>
        
          <v-row>
            <v-col>
              <v-text-field
                v-model="project.projectName"
                label="프로젝트 이름"
                :readonly="mode === 'D'"
              ></v-text-field>
            </v-col>
            <v-col>
              <label> 프로젝트 기간</label><br>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="project.projectStDate"/>  ~
              <input type="date" id="endDate" :readonly="mode === 'D'" v-model="project.projectEndDate"/>  
            </v-col>
          </v-row>
          <v-row>
           <v-col>
             <v-select
            label="진행상태"
            :items="['진행', '완료', '연장']"
            v-model="project.projectStatus"
            :readonly="mode === 'D'"
            >
            </v-select>
           </v-col>
          </v-row>
          <v-row >
            <v-col>
              
              <v-text-field
              label="고객사"
              :readonly="mode === 'D'"
              v-model="project.customer">
              </v-text-field>
              
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-textarea v-model="project.remark" :readonly="mode === 'D'" label="비고" variant="outlined" rows="10"></v-textarea>
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
      console.log("@@@@@@@@@@@@@@");
      
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
      project : {
        projectId : '',
        projectName : '', // 프로젝트명
        projectStDate : '', // 프로젝트 시작일
        projectEndDate : '', // 프로젝트 종료일
        projectStatus : '', // 프로젝트 상태
        customer : '', // 고객사
        remark : '', // 비고
      }
    }
  },

  methods: {
      close:()=>{
        store.commit("toggleModal");
      },

      // 프로젝트 정보 등록
      async newProject(){
       await projectApi.newProject(this.project);
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
     

    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
