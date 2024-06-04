<template>
  <ModalLayout
   @close="this.$emit('close')"
  >
    <PdfPrevModal
      v-if="bPdfPrevModal"
      @close="bPdfPrevModal = false"
      :pdfFileLink="pdfFileLink"
      />

    <div class="modal-title">
      계약 관리{{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">
      <v-container>
          <v-row>
            <v-col v-if="contr.contrFileId == ''">
              <v-file-input label="계약파일" @change="selectFile" ></v-file-input>
            </v-col>
            <v-col v-if="contr.contrFileId != ''">
              <div style="padding: 10px; display: inline-block">
                <v-icon icon="mdi-delete" @click="contr.contrFileId ='' "></v-icon>
                <v-icon
                  icon="mdi-arrow-up-bold-box-outline"
                  size="large"
                ></v-icon>
                <a :href="`${REQUEST_URL}/common/download/${contr.contrFileId}`">{{contr.orignFileName}}</a>
                &nbsp;&nbsp;
                <v-btn
                  v-if="contr.contrFileId != ''"
                  color="green"
                  density="compact"
                  @click="openPdfPrevModal"
                >미리보기</v-btn>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="원계약"
                readonly
                v-model="contr.topContrNm"
                variant="outlined"
                >
              </v-text-field>
            </v-col>

             <v-col>
              <v-text-field
              label="업체"
                readonly
                v-model="contr.compNm"
                variant="outlined"
                >
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
           <v-col>
              <v-text-field
              label="계약명"
              :readonly="mode === 'R'"
              v-model="contr.contrNm"
              :rules="[utils.required]"
              variant="outlined"
              >
              </v-text-field>
            </v-col>
             <v-col>
              <v-text-field
              label="계약금액"
              :readonly="mode === 'R' || mode === 'D'"
              :rules="[utils.required]"
              variant="outlined"
              v-model="contr.contrAmount">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row >
            <v-col>
              <v-text-field
              label="지불조건"
              :readonly="mode === 'R'"
              :rules="[utils.required]"
              variant="outlined"
              v-model="contr.paymentTerm">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="계약시작일자"
                type="date"
                :readonly="mode === 'R'"
                :rules="[utils.required]"
                variant="outlined"
                v-model="contrStDate"
              >
              </v-text-field>

            </v-col>

            <v-col>
              <v-text-field
                label="계약종료일자"
                type="date"
                :readonly="mode === 'R'"
                :rules="[utils.required]"
                variant="outlined"
                v-model="contrEndDate"
              >
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-textarea
               label="특이사항"
               variant="outlined"
               rows="5"
               :readonly="mode === 'R'"
               v-model="contr.specialNote"
               ></v-textarea>
            </v-col>
          </v-row>

          <v-row>
             <div class="modal-btn-list">
              <v-btn
                    color="blue"
                    v-if="mode !== 'D'"
                    @click="updateMode()"
                  >수동등록</v-btn>
                  　
                <v-btn
                  color="blue"
                  v-if="mode !== 'D'"
                  @click="newContr"
                >확정</v-btn>
                <v-btn
                  color="blue"
                  v-if="mode !== 'R'"
                  @click="newContr"
                >수정</v-btn>
            </div>
          </v-row>
      </v-container>

    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;
</script>

<script>
import store from "@/store/store";

const BE_PORT = import.meta.env.VITE_BE_PORT;
import {MODAL_MODE} from "@/util/config";
import commonApi from "@/api/common.js"
import utils from "@/util/validUtil";
import contrApi from '@/api/contr';
import companyApi from '@/api/company';
import topContrApi from '@/api/project';
import PdfPrevModal from "@/components/modal/PdfPrevModal.vue"

export default {
  name: "ProjectModal",

  beforeMount(){
    //상세조회
    if(this.mode == 'D'){
        /** 계약 단건 조회*/
        console.log("sssss");
        this.detailContr();
    }else{

      this.getTopContrNm();
      this.getCompNm();
    }

  },

  mounted(){
    if(this.mode == 'D'){
    }
  },

  data() {
    return {
      visible : false,

      mode: store.getters.getParams.mode,

      key:store.getters.getParams.key,

      contrStDate : '',

      contrEndDate : '',

      bPdfPrevModal : false,

      pdfFileLink: '',

      contr : {
        contrId :'',
        topContrNm : '',
        topContrId : '',
        estimateId : '',
        compId : '',
        companyNm : '',
        contrNm : '',
        contrAmount : '',
        paymentTerm : '',
        specialNote : '',
        contrStDate : '',
        contrEndDate : '',
        contrFileId : '' ,
        orignFileName : '',
      },

      params : store.getters.getParams,

      file : '',
    }
  },

  methods: {
      close:()=>{
        store.commit("toggleModal");
      },

      /**
       *  원계약명 조회
       * @param {topContrId}
       * */
      async getTopContrNm() {
        console.log(this.contr.contrFileId);
        const topContrId = this.params.topContrId;

        const data = await commonApi.topContrNm(topContrId);

        this.contr.topContrNm = data.topContrNm;

      },

      /**
       *  업체명 조회
       * @param {compId}
       * */
      async getCompNm() {
        const compId = this.params.compId;

        const data = await commonApi.compNm(compId);

        this.contr.compNm = data.compNm;
      },


      /**
       * 계약 정보 및 계약서 파일 저장
      */
      async newContr(){



        if(utils.isNull(this.file) && utils.isNull(this.contr.contrFileId)){
          alert("파일을 선택해주세요.");
          return false;
        }

        const formData = new FormData();
        formData.append('file' ,this.file);

        const amount = this.contr.contrAmount;

        this.contr.contrAmount = amount.replace(",", "");

        this.contr.contrStDate = utils.saveDate(this.contrStDate);
        this.contr.contrEndDate = utils.saveDate(this.contrEndDate);


        this.contr.estimateId = this.params.estimateId;

        const blob = new Blob([JSON.stringify(this.contr)],{type:'application/json'});

        formData.append('data' ,blob);

        if(this.MODE ==='R'){
          if (!confirm("등록 하시겠습니까?")) return false;
        }else{
          if (!confirm("수정 하시겠습니까?")) return false;
        }


        //계약 저장
        await contrApi.newContr(formData);

        this.$emit("close");
        this.$emit("getContrs");
      },

      // 계약 단건조회
      async detailContr(){

        this.contr = await contrApi.contr(this.key);
        
        this.contrStDate = this.contr.contrStDate; // 계약시작일자
        this.contrEndDate = this.contr.contrEndDate; // 계약종료일자


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
          console.log("this.mode =>"+ this.mode);
      },

      /**
       * 파일 선택
      */
      selectFile(file){
        this.file = file.target.files[0];

        this.saveContrFile();
      },

      /**
       * 계약 파일 저장
       *
      */
      async saveContrFile(){

        const formData = new FormData();
        formData.append('file' ,this.file);

        const data = await contrApi.saveContrFile(formData);


        if(data.contrNm == null){

          alert("자동등록에 실패하였습니다.");
          this.contr.contrNm     = '';
          this.contr.contrAmount = '';
          this.contr.paymentTerm = '';
          this.contr.specialNote = '';
          this.contrStDate = '';
          this.contrEndDate = '';
          this.file = '';
          return false;

        }else{

          this.contr.contrNm     = data.contrNm;
          this.contr.contrAmount = data.contrAmount;
          this.contr.paymentTerm = data.paymentTerm;
          this.contr.specialNote = data.specialNote;
          this.contrStDate = utils.formatDate(data.contrStDate);
          this.contrEndDate = utils.formatDate(data.contrEndDate);

          // 파일 등록시 수정모드로 변경
          this.updateMode();
        }

      },

      /* pdf 미리보기 */
      openPdfPrevModal() {
        const BASE_URL = import.meta.env.VITE_BASE_URL;
        const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
        const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

        this.pdfFileLink = `${REQUEST_URL}/common/download/${this.contr.contrFileId}`;
        this.bPdfPrevModal = true;

      },

    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
