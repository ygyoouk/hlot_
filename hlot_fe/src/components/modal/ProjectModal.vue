<template>
 <ModalLayout
    @close="this.$emit('close')">
    
  <CompnaySearchModal
    v-if="bCompanySearchModal"
    :compDiv="compDiv"
    @select="selectNm"
    @close="bCompanySearchModal = !bCompanySearchModal"
  />

    <PdfPrevModal
      v-if="bPdfPrevModal"
      @close="bPdfPrevModal = false"
      :pdfFileLink="pdfFileLink"
    />

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
                variant="outlined"
                color="primary"
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
                :readonly="mode === 'D' || mode === 'R'"
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
                variant="underlined"
                color="primary"
                :rules="[utils.required]"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="품명"
                density="comfortable"
                variant="underlined"
                color="primary"
                :rules="[utils.required]"
                v-model="topContr.prodNm">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="총용역부기금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                :rules="[utils.required, utils.number]"
                v-model="topContr.totalServBokAmount">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="계약금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                :rules="[utils.required, utils.number]"
                v-model="topContr.contrAmount">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                label="1차수 금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                v-model="topContr.amout1">
              </v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                label="2차수 금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                v-model="topContr.amout2">
              </v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                label="3차수 금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                v-model="topContr.amout3">
              </v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                label="4차수 금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                v-model="topContr.amout4">
              </v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                label="5차수 금액"
                variant="underlined"
                color="primary"
                density="comfortable"
                :readonly="mode === 'D'"
                v-model="topContr.amout5">
              </v-text-field>
            </v-col>
          </v-row>


          <v-row>
            <v-col>
              <v-text-field
                label="계약시작일자"
                type="date"
                variant="outlined"
                color="primary"
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
                variant="outlined"
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
                variant="outlined"
                :rules="[utils.required]"
                type="date"
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
                :readonly="mode === 'D' || mode === 'R'"
                @click:append-inner="compSearchPopUp('COMP02')"
                v-model="topContr.demandInst">
              </v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-select
                label="원계약구분"
                :items="topContrDivs"
                variant="underlined"
                color="primary"
                item-title="codeNm"
                item-value="code"
                :rules="[utils.required]"
                density="comfortable"
                v-model="topContr.topContrDiv"
              />
            </v-col>
          </v-row>

          <v-row>
            <v-col v-if=" topContr.fileId === '' || topContr.fileId === null ">
              <v-file-input density="comfortable" label="원계약파일" :rules="[utils.required]" @change="selectFile"></v-file-input>
            </v-col>
            <v-col v-else>
              <div style="padding: 10px; display: inline-block">
              
                <v-icon icon="mdi-delete" @click="topContr.fileId ='' "></v-icon>

                <a :href="`${REQUEST_URL}/common/download/${topContr.fileId}`">
                  <v-icon
                    icon="mdi-arrow-up-bold-box-outline"
                    size="large"
                  ></v-icon>
                  {{ topContr.orignFileName }}
                  &nbsp;&nbsp;
                  <v-btn v-if="topContr.fileId !== '' && topContr.fileId !=null"
                         density="compact"
                         color="green"
                         @click="openPdfPrevModal"
                  >
                    미리보기
                  </v-btn>
                </a>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <div class="modal-btn-list">
              <v-btn
                v-if="mode ==='R'"
                color="green"
                @click="newProject"
              >저장</v-btn>
              　
              <v-btn
                v-if="mode ==='R'"
                color="green"
                @click="tempProject"
              >임시저장</v-btn> 
              <v-btn
                v-if="mode ==='D'"
                color="green"
                @click="newProject"
              >수정</v-btn>
            </div>
          </v-row>
        </v-form>
      </v-container>

    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import CompnaySearchModal from "@/components/modal/search/CompanySearch.vue";
import PdfPrevModal from "@/components/modal/PdfPrevModal.vue";
const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;
</script>

<script>
import store from "@/store/store";

import {MODAL_MODE} from "@/util/config";
import utils from "@/util/validUtil";
import projectApi from '@/api/project.js'
import commonApi from "@/api/common"

export default {
  name: "ProjectModal",

  async beforeMount(){
    this.topContrDivs = await commonApi.cmmCodeComp('TCTR');

    //상세조회
    if(this.mode === 'D'){
      await this.getProject();
    }

    if(this.mode === 'R'){
       
       if(localStorage.length>0 && localStorage.getItem("topContr") != null){
          console.log(JSON.parse(window.localStorage.getItem('topContr')));
          await this.readTempProject();
       }

    }
  },

  mounted(){
    console.log(this.topContr.fileId);
    if(this.mode === 'D'){

    }

    if(this.topContr.topContrNm !== ''){
      this.visible = true;
    }
  },

  data() {
    return {
      visible: false,
      number_valid: v => /^[0-9]+$/.test(v) || '숫자만 입력 가능합니다',
      mode: store.getters.getParams.mode,

      key: store.getters.getParams.key,

      bCompanySearchModal: false,

      bPdfPrevModal: false,

      compDiv: '',

      pdfFileLink: '',

      topContrDate: '',
      contrStDate: '',
      contrEndDate: '',
      deliveryDeadline: '',

      topContrDivs: [],

      topContr: {
        topContrId: '',
        topContrNm: '',  // 원계약명
        topContrDiv: '', // 원계약구분
        clientComp: '', // 발주처
        clientCompId: '', // 발주처ID
        topContrDate: '', // 원계약일자
        contrStDate: '', // 계약시작일자
        contrEndDate: '', // 계약종료일자
        prodNm: '', // 품명
        totalServBokAmount : '', // 총용역부기금액
        contrAmount: '', // 계약금액
        amout1: '', // 1차수 금액
        amout2: '', // 2차수 금액
        amout3: '', // 3차수 금액
        amout4: '', // 4차수 금액
        amout5: '', // 5차수 금액
        deliveryDeadline: '', // 납품기한
        demandInst: '', // 수요기관명
        demandInstId: '', // 수요기관ID
        topContrFileId: '',  // 첨부파일ID
        fileId: '', // 파일ID
        filePath: '', // 파일 경로
        orignFileName: '', // 파일 원본명
      },
      image: '',
    }
  },

  methods: {

    // 프로젝트 정보 등록
    async newProject() {

      console.log("mode ===>" + this.mode)

      const formData = new FormData();
      formData.append('file', this.image);

      const blob = new Blob([JSON.stringify(this.topContr)], {type: 'application/json'});

      formData.append('data', blob);

      this.topContr.contrStDate = utils.saveDate(this.contrStDate);
      this.topContr.contrEndDate = utils.saveDate(this.contrEndDate);
      this.topContr.topContrDate = utils.saveDate(this.topContrDate);
      this.topContr.deliveryDeadline = utils.saveDate(this.deliveryDeadline);

      console.log(JSON.stringify(this.topContr));

      const {valid} = await this.$refs.form.validate();
      if (!valid) return false;

      if(this.MODE ==='R'){
        if (!confirm("등록 하시겠습니까?")) return false;
      }else{
        
        if (!confirm("수정 하시겠습니까?")) return false;
      }
      

      await projectApi.newProject(formData);

      this.$emit("close");
      this.$emit("searchTopContr");

    },

    /*임시저장*/
    tempProject(){

      if(!confirm("임시저장하시겠습니까?")) return false;

      this.topContr.contrStDate = utils.saveDate(this.contrStDate);
      this.topContr.contrEndDate = utils.saveDate(this.contrEndDate);
      this.topContr.topContrDate = utils.saveDate(this.topContrDate);
      this.topContr.deliveryDeadline = utils.saveDate(this.deliveryDeadline);

      window.localStorage.setItem("topContr",JSON.stringify(this.topContr));
      
      alert("임시저장되었습니다");
      
    },

    /*임시저장 뿌려주기*/
    async readTempProject(){
       if(!confirm("임시저장된 내용을 사용하시겠습니까?")){
        localStorage.removeItem("topContr");
        return false;
       } 

      this.topContr = JSON.parse(window.localStorage.getItem('topContr'));

      this.contrStDate = utils.formatDate(this.topContr.contrStDate); // 원계약시작일자
      this.contrEndDate = utils.formatDate(this.topContr.contrEndDate); // 원계약종료일자
      this.topContrDate = utils.formatDate(this.topContr.topContrDate);  // 원계약일자
      this.deliveryDeadline = utils.formatDate(this.topContr.deliveryDeadline); // 납품기한      
      
    },

    /**
     * 원계약기간 valid
     * 시작일자 > 종료일자
     */
    checkDateValid() {
      if (utils.isNull(this.topContr.contrEndDate)) return false;

      if (!utils.validDateChk(this.topContr.contrStDate, this.topContr.contrEndDate)) return false;
    },

    /**
     * 원계약 validation checks
     */
    validSaveTopContr() {
      const {valid} = this.$refs.form.validate();

      if (!valid) return false;
    },

    /**
     * 프로젝트 단건조회
     * */
    async getProject() {
      this.topContr = await projectApi.project(this.key) || {};

      this.contrStDate = utils.formatDate(this.topContr.contrStDate); // 원계약시작일자
      this.contrEndDate = utils.formatDate(this.topContr.contrEndDate); // 원계약종료일자
      this.topContrDate = utils.formatDate(this.topContr.topContrDate);  // 원계약일자
      this.deliveryDeadline = utils.formatDate(this.topContr.deliveryDeadline); // 납품기한
    },

    compSearchPopUp(compDiv) {
      console.log(compDiv);
      // if (this.mode !== MODAL_MODE.REG) return false;

      this.compDiv = compDiv;

      this.bCompanySearchModal = !this.bCompanySearchModal;
    },

    /**
     *발주처,수요기관 검색한 내용 가져오기
     */
    selectNm(obj) {
      const compDiv = obj.div;

      // 발주처 :  COMP03 / 수요기관 :  COMP02
      if (compDiv === "COMP03") {
        this.topContr.clientComp = obj.nm;
        this.topContr.clientCompId = obj.id;
      } else {
        this.topContr.demandInst = obj.nm;
        this.topContr.demandInstId = obj.id;
      }
    },

    // 모드 변경
    updateMode() {
      this.mode = 'M';
    },

    selectFile(file) {
      this.image = file.target.files[0];
    },

    /* pdf 미리보기 */
    openPdfPrevModal() {
      const BASE_URL = import.meta.env.VITE_BASE_URL;
      const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
      const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

      this.pdfFileLink = `${REQUEST_URL}/common/download/${this.topContr.topContrFileId}`;
      this.bPdfPrevModal = true;

    },


  }
}
</script>

<style>
@import "@/assets/styles/modal.css";
</style>
