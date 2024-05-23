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
        <v-form ref="form">
          <v-row>
            <v-col>
              <v-text-field 
                label="원계약일자"
                type="date" 
                :readonly="mode === 'D'" 
                v-model="topContrDate" 
                :rules="[utils.required]">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
              label="발주처"
              density="comfortable"
              append-inner-icon="mdi-magnify"
              readonly="readonly"
              :rules="[utils.required]"
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
                :rules="[utils.required]"
                :readonly="mode === 'D'"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
             <v-col>
              <v-text-field
              label="품명"
              density="comfortable"
              :rules="[utils.required]"
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
              :rules="[utils.required,number_valid]"
              v-model="topContr.contrAmount">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="계약시작일자"
                type="date" 
                :readonly="mode === 'D'" 
                :rules="[utils.required]"
                v-model="contrStDate"
                @change="checkDateValid"
              >
              </v-text-field>
            </v-col>
            
            <v-col>
              <v-text-field
                label="계약종료일자"
                type="date" 
                :readonly="mode === 'D'" 
                :rules="[utils.required]"
                v-model="contrEndDate"
                @change="checkDateValid"
              >
              </v-text-field>
            </v-col>
          </v-row>
         
          <v-row>
             <v-col>
              <v-text-field
                label="납품기한"
                :rules="[utils.required]"
                type="date" 
                :readonly="mode === 'D'" 
                v-model="deliveryDeadline"
              >
              </v-text-field>
            </v-col>
          </v-row>
          
          <v-row>
            <v-col>
              <v-text-field
              label="수요기관명"
              density="comfortable"
              append-inner-icon="mdi-magnify"
              :rules="[utils.required]"
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
            :rules="[utils.required]"
            :items="['유지관리', '구축', '개발']"
            v-model="topContr.topContrDiv"
            :readonly="mode === 'D'"
            >
            </v-select>
           </v-col>
          </v-row>

          <v-row>
            <v-col v-if="this.topContr.fileId == '' ">
              <v-file-input density="comfortable" label="원계약파일" :rules="[utils.required]" @change="selectFile"></v-file-input>
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
        </v-form>
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
      number_valid : v => /^[0-9]+$/.test(v) || '숫자만 입력 가능합니다',
      mode: store.getters.getParams.mode,

      key:store.getters.getParams.key,

      bCompanySearchModal : false,

      compDiv : '',

      topContrDate : '',
      contrStDate : '',
      contrEndDate : '',
      deliveryDeadline : '',

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

        const blob = new Blob([JSON.stringify(this.topContr)],{type:'application/json'});

        formData.append('data' ,blob);

        this.topContr.contrStDate = utils.saveDate(this.contrStDate);
        this.topContr.contrEndDate = utils.saveDate(this.contrEndDate);
        this.topContr.topContrDate = utils.saveDate(this.topContrDate);
        this.topContr.deliveryDeadline = utils.saveDate(this.deliveryDeadline);

        const { valid } = await this.$refs.form.validate();
        if(!valid) return false;

        if(!confirm("등록 하시겠습니까?")) return false;

        await projectApi.newProject(formData);

        this.$emit("close");
      },

      /**
       * 원계약기간 valid
       * 시작일자 > 종료일자
      */
      checkDateValid(){
        if(utils.isNull(this.topContr.contrEndDate)) return false;

        if(!utils.validDateChk(this.topContr.contrStDate, this.topContr.contrEndDate))return false;
      },

      /**
       * 원계약 validation checks
      */
      validSaveTopContr(){

        const { valid } =  this.$refs.form.validate();

        if(!valid) return false;

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
          this.topContr.demandInst = obj.nm;
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
