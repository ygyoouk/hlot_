<template>
<CompnaySearchModal
  v-if="bCompanySearchModal"
  :compDiv="compDiv"
  @select="selectNm"
  @close="bCompanySearchModal = !bCompanySearchModal"
  />
  <ModalLayout
    @close="this.$emit('close')">

    <div class="modal-title">
      원계약 관리{{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">

      <v-container>

          <v-row>
            <v-col>
              <label for="strDate">원계약일자  : </label>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="topContr.topContrDate"/>
            </v-col>
          </v-row>
          <v-row>
            <br>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
              label="발주처"
              density="comfortable"
              append-inner-icon="mdi-magnify"
              readonly="readonly"
              @click:append-inner="compSearchPopUp('COMP03')"
              v-model="topContr.clientComp">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                v-model="topContr.topContrNm"
                density="comfortable"
                label="원계약명"
                :readonly="mode === 'D'"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
             <v-col>
              <v-text-field
              label="품명"
              density="comfortable"
              :readonly="mode === 'D'"
              v-model="topContr.prodNm">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
              label="계약금액"
              density="comfortable"
              :readonly="mode === 'D'"
              v-model="topContr.contrAmount">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <label> 원계약 기간 : </label>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="topContr.contrStDate"/>  ~
              <input type="date" id="endDate" :readonly="mode === 'D'" v-model="topContr.contrEndDate"/>
            </v-col>
          </v-row>
          <v-row>
            <br>
          </v-row>
          <v-row>
             <v-col>
              <label>납품기한 : </label>
              <input type="date" id="strDate" :readonly="mode === 'D'" v-model="topContr.deliveryDeadline"/>
            </v-col>
          </v-row>
          <v-row>
            <br>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
              label="수요기관명"
              density="comfortable"
              append-inner-icon="mdi-magnify"
              :readonly="mode === 'D'"
              @click:append-inner="compSearchPopUp('COMP02')"
              v-model="topContr.demandInst">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
           <v-col>
             <v-select
            label="구분"
            density="comfortable"
            :items="['유지관리', '구축', '개발']"
            v-model="topContr.topContrDiv"
            :readonly="mode === 'D'"
            >
            </v-select>
           </v-col>
          </v-row>

          <v-row>
            <v-col v-if="this.topContr.fileId == '' ">
              <v-file-input density="comfortable" label="원계약파일" @change="selectFile" ></v-file-input>
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
import CompnaySearchModal from "@/components/modal/search/CompanySearch.vue"

export default {
  name: "ProjectModal",

  beforeMount(){
    //상세조회
    if(this.mode == 'D'){
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

      bCompanySearchModal : false,

      compDiv : '',

      topContr : {
        topContrId : '',
        topContrNm : '',  // 원계약명
        topContrDiv : '', // 원계약구분
        clientComp : '', // 발주처
        clientCompId : '', // 발주처ID
        topContrDate : '', // 원계약일자
        contrStDate : '', // 계약시작일자
        contrEndDate : '', // 계약종료일자
        prodNm : '', // 품명
        contrAmount : '', // 계약금액
        deliveryDeadline : '', // 납품기한
        demandInst : '', // 수요기관명
        demandInstId : '', // 수요기관ID
        topContrFileId : '',  // 첨부파일ID
        fileId : '', // 파일ID
        filePath : '', // 파일 경로
        orignFileName : '', // 파일 원본명
      },
      image : '',
    }
  },

  methods: {

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

        if(utils.isNull(this.topContr.topContrNm)){
          alert('원계약명을 입력해주세요');
           return false;
        }

        if(!confirm("등록 하시겠습니까?")) return false;

        await projectApi.newProject(formData);
        this.$emit("close");
      },

      /**
       * 프로젝트 단건조회
       * */ 
      async getProject(){
        this.topContr = await projectApi.project(this.key);

        this.topContr.contrStDate = utils.formatDate(this.topContr.contrStDate); // 원계약시작일자
        this.topContr.contrEndDate = utils.formatDate(this.topContr.contrEndDate); // 원계약종료일자
        this.topContr.topContrDate = utils.formatDate(this.topContr.topContrDate);  // 원계약일자
        this.topContr.deliveryDeadline = utils.formatDate(this.topContr.deliveryDeadline); // 납품기한
      },

      compSearchPopUp(compDiv){
        if(this.mode !== MODAL_MODE.REG) return false;
        this.compDiv = compDiv;
        this.bCompanySearchModal =!this.bCompanySearchModal;
      },

    /**
     *발주처,수요기관 검색한 내용 가져오기
    */
    selectNm(obj){
      const compDiv = obj.div;

        // 발주처 :  COMP03 / 수요기관 :  COMP02
        if(compDiv === "COMP03"){
          this.topContr.clientComp   = obj.nm;
          this.topContr.clientCompId = obj.id;
        }else{
          this.topContr.demandInstNm = obj.nm;
          this.topContr.demandInstId = obj.id;
        }
      },

      // 모드 변경
      updateMode(){
          this.mode = 'M';
      },

      selectFile(file){
        this.image = file.target.files[0];
      }


    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
