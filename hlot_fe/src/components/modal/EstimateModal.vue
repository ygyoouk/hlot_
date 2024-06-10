<template>

  <ModalLayout
    @close="modalClose"
  >

    <TopContrSearch
      v-if="bTopContrSearch"
      @close="bTopContrSearch = !bTopContrSearch"
      @select="selectTopContr"
    />

    <CompanySearch
      v-if="bCompanySearch"
      @close="bCompanySearch = !bCompanySearch"
      @select="selectCompany"
      :compDiv="compDiv"
    />

    <PdfPrevModal
      v-if="bPdfPrevModal"
      @close="bPdfPrevModal = false"
      :pdfFileLink="pdfFileLink"
    />


    <div class="modal-title">
      견적서 등록
    </div>

    <div class="modal-content">
      <v-container>
        <v-form ref="form">

          <v-row>
            <v-col>
              <v-text-field
                label="원계약 명"
                readonly="readonly"
                append-inner-icon="mdi-magnify"
                @click:append-inner="openTopContrSearch"
                v-model="estimate.topContrNm"
                :rules="[validUtil.required]"
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                label="업체"
                readonly="readonly"
                append-inner-icon="mdi-magnify"
                @click:append-inner="openCompanySeach('COMP01')"
                v-model="estimate.compNm"
                :rules="[validUtil.required]"
              ></v-text-field>
            </v-col>
            <v-col>
              <v-select
                label="견적구분(대)"
                :items="estimateDivs"
                item-title="codeNm"
                item-value="code"
                v-model="estimate.estimateDiv"
                :readonly="!estimate.possibleConfirm"
                :rules="[validUtil.required]"
                @update:modelValue="filterDiv"

                variant="outlined"
                color="primary"
                ></v-select>
            </v-col>
            <v-col>
              <v-select
                label="견적구분(소)"
                :items="fEstimateLowDivs"
                item-title="codeNm"
                item-value="code"
                v-model="estimate.estimateLowDiv"
                :readonly="!estimate.possibleConfirm"

                variant="outlined"
                color="primary"
              ></v-select>
            </v-col>
          </v-row>
        </v-form>
        <v-row>
          <v-col>
            <br>
          </v-col>
        </v-row>
        <v-row>
          <v-col v-if="mode === MODAL_MODE.REG">
            <v-file-input label="견적서" @change="selectFile" :readonly="mode === MODAL_MODE.DETAIL"></v-file-input>
          </v-col>
          <v-col v-else>
            <div style="padding: 10px; display: inline-block">

              <a :href="`${REQUEST_URL}/common/download/${estimate.attachmentVO.fileId}`">
                <v-icon
                  icon="mdi-arrow-up-bold-box-outline"
                  size="large"
                ></v-icon>
                {{ estimate.attachmentVO.orignFileName }}
              </a>
              &nbsp;&nbsp;
              <v-btn
                density="compact"
                color="green"
                @click="openPdfPrevModal"
              >
                미리보기
              </v-btn>

            </div>
          </v-col>
        </v-row>
        <v-row>
          <v-col><br><hr><br></v-col>
        </v-row>
        <v-row>
          <v-col style="font-size: 20px">품목</v-col>
          <v-col style="text-align: right">
            <v-btn
              v-if="estimate.possibleConfirm"
              @click="addProd()"
              color="green">품목 추가</v-btn>
            <br><br>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-form ref="prodForm">
              <table class="custom-table_mt">
                <thead>
                <tr>
                  <th>순번</th>
                  <th>품명</th>
                  <th>세부품명</th>
                  <th>소비자단가</th>
                  <th>공급단가</th>
                  <th>할인율</th>
                  <th>수량</th>
                  <th>-</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(prod, index) in estimate.prods">
                  <td>{{ prod.orderNo }}</td>
                  <td style="width: 200px"> <!-- 품명 -->
                    <v-text-field
                      v-model="prod.prodNm"
                      density="compact"
                      :readonly="!estimate.possibleConfirm"
                      :rules="[validUtil.required]"
                      variant="outlined"
                      color="primary"
                    />
                  </td>
                  <td style="width: 250px"> <!-- 세부품명 -->
                    <v-text-field
                      v-model="prod.detailProdNm"
                      density="compact"
                      :readonly="!estimate.possibleConfirm"
                      :rules="[validUtil.required]"
                      variant="outlined"
                      color="primary"
                    />
                  </td>
                  <td> <!-- 소비자단가 -->
                    <v-text-field
                      v-model="prod.clientUnitPrice"
                      density="compact"
                      :readonly="!estimate.possibleConfirm"
                      :rules="[validUtil.number]"
                      variant="outlined"
                      color="primary"

                      @input="validUtil.addComma"
                      @focusin="validUtil.addComma"
                      @focusout="validUtil.addComma"
                    />
                  </td>
                  <td> <!-- 공급단가 -->
                    <v-text-field
                      v-model="prod.provProdPrice"
                      density="compact"
                      :readonly="!estimate.possibleConfirm"
                      :rules="[validUtil.number]"
                      variant="outlined"
                      color="primary"

                      @input="validUtil.addComma"
                      @focusin="validUtil.addComma"
                      @focusout="validUtil.addComma"
                    />
                  </td>
                  <td> <!-- 할인율 -->
                    <v-text-field
                      v-model="prod.dcPer"
                      density="compact"
                      :readonly="!estimate.possibleConfirm"
                      :rules="[validUtil.number]"
                      variant="outlined"
                      color="primary"
                    />
                  </td>
                  <td> <!-- 수량 -->
                    <v-text-field
                      v-model="prod.quantity"
                      density="compact"
                      :readonly="!estimate.possibleConfirm"
                      :rules="[validUtil.number]"
                      variant="outlined"
                      color="primary"
                    />
                  </td>
                  <td style="text-align: center; width: 30px">
                    <v-btn
                      v-if="estimate.possibleConfirm"
                      color="red"
                      density="compact"
                      @click="deleteProd(index)"
                    >행 삭제</v-btn>
                  </td>
                </tr>
                </tbody>
              </table>
            </v-form>
          </v-col>
        </v-row>
        <v-row>
          <div class="modal-btn-list">
            <v-btn
              v-if="estimate.possibleConfirm && mode !== MODAL_MODE.REG"
              color="primary"
              @click="confirmEstimate"
            >확정</v-btn> &nbsp;&nbsp;&nbsp;&nbsp;

            <v-btn
              v-if="estimate.possibleConfirm"
              color="green"
              @click="newEstimate"
            >저장</v-btn>
            　
            <v-btn
              v-if="estimate.possibleConfirm"
              color="red"
              @click="deleteEstimate"
            >삭제</v-btn>
            <br>
            <sub v-if="estimate.possibleConfirm  && mode !== MODAL_MODE.REG" style="color: red; margin-right: 150px;">확정시 수정이 불가합니다.</sub>
          </div>
        </v-row>
      </v-container>
    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";
import TopContrSearch from "@/components/modal/search/TopContrSearch.vue";
import CompanySearch from "@/components/modal/search/CompanySearch.vue";
import PdfPrevModal from "@/components/modal/PdfPrevModal.vue"

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;
</script>

<script>
import store from "@/store/store";
import commonApi from "@/api/common.js"
import estimateApi from "@/api/estimate.js";
import prodApi from "@/api/prod.js"
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";



export default {
  name: "EstimateModal",
  async beforeMount() {

    this.estimateDivs = await commonApi.cmmCodeComp('ESTD');
    this.estimateLowDivs = await commonApi.cmmCodeComp('ESTDL');

    if(!validUtil.isNull(this.key)){
      await this.getEstimate();

      let temp = this.estimate.estimateLowDiv;
      this.filterDiv();
      this.estimate.estimateLowDiv = temp;

      await this.getProds();
    } else {
      this.estimate.possibleConfirm = true;
    }

  },
  data() {
    return {
      compDiv: '',
      bTopContrSearch: false,
      bCompanySearch: false,
      bPdfPrevModal: false,

      pdfFileLink: '',

      mode: store.getters.getParams.mode,
      key: store.getters.getParams.key,

      file: '',
      estimateDivs: [],
      estimateLowDivs: [],
      fEstimateLowDivs: [],

      estimate: {
        topContrId: '',
        topContrNm: '',

        compId: '',
        compNm: '',

        estimateDiv: '',
        estimateLowDiv: '',

        attachmentVO:{
          fileId: '',
          orignFileName: '',
        },
        possibleConfirm: false,

        prods: [],
      },

    }
  },
  methods : {
    /* 원계약명 조회 */
    openTopContrSearch() {
      if(this.mode !== MODAL_MODE.REG) return false;
      this.bTopContrSearch = !this.bTopContrSearch;
    },
    selectTopContr(obj) {
      this.estimate.topContrId = obj.id;
      this.estimate.topContrNm = obj.nm;
    },
    /* 업체 조회 */
    openCompanySeach(compDiv) {
      if(this.mode !== MODAL_MODE.REG) return false;
      this.compDiv = compDiv;
      this.bCompanySearch = !this.bCompanySearch;
    },
    selectCompany(obj) {
      this.estimate.compNm = obj.nm;
      this.estimate.compId = obj.id;
    },
    /* pdf 미리보기 */
    openPdfPrevModal() {
      const BASE_URL = import.meta.env.VITE_BASE_URL;
      const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
      const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

      this.pdfFileLink = `${REQUEST_URL}/common/download/${this.estimate.attachmentVO.fileId}`;
      this.bPdfPrevModal = true;
    },

    /* 파일 선택 */
    selectFile(file) {
      this.file = file.target.files[0];
    },

    /* ESTIMATE 상세조회 */
    async getEstimate() {
      this.estimate = await estimateApi.estimate(this.key);
      this.estimate.attachmentVO = validUtil.isNull(this.estimate.attachmentVO) ? {} : this.estimate.attachmentVO;
    },

    /* PROD 목록조회 */
    async getProds() {
      this.prods = await prodApi.prods(this.key);
    },

    /* PROD 추가 */
    async addProd() {
      this.estimate.prods.push({});
    },
    /* PROD 삭제 */
    deleteProd(index) {
      if(!confirm("삭제 하시겠습니까?")) return false;

      this.estimate.prods.splice(index, 1);
    },

    /* ESTIMATE 등록 */
    async newEstimate(){
      const { valid } = await this.$refs.form.validate();
      if(!valid) return false;

      if(this.estimate.prods.length > 0){
        const prodFormValid = (await this.$refs.prodForm.validate()).valid;
        if(!prodFormValid) return false;
      }

      if(!confirm("등록 하시겠습니까?")) return false;

      const blob = new Blob([JSON.stringify(this.estimate)], {type:'application/json'});

      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('data', blob);

      await estimateApi.newEstimate(formData);

      this.modalClose();

    },
    /* ESTIMATE 삭제 */
    async deleteEstimate(){
      await estimateApi.deleteEstimate(this.key);
      this.modalClose();
    },
    /* ESTIMATE 확정 */
    async confirmEstimate(){
      if(!confirm("확정 하시겠습니까?")) return false;

      await estimateApi.confirmEstimate(this.key);

      this.modalClose();
    },

    /* 견적하위구분 필터링 */
    filterDiv() {
      this.estimate.estimateLowDiv = '';
      this.fEstimateLowDivs = this.estimateLowDivs.filter(e => {
        return e.upperCode === this.estimate.estimateDiv;
      });
    },

    modalClose(){
      this.$emit('update');
      this.$emit('close');
    }


  }
}
</script>

<style scoped>
  @import "@/assets/styles/modal.css";
  @import "@/assets/styles/customTable.css";
</style>
