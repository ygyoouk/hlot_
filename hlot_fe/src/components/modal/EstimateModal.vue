<template>
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


  <ModalLayout
    @close="modalClose"
  >

    <div class="modal-title">
      견적서 등록
    </div>

    <div class="modal-content">
      <v-container>
        <v-row>
          <v-col>
            <v-text-field
              label="원계약 명"
              readonly="readonly"
              append-inner-icon="mdi-magnify"
              @click:append-inner="openTopContrSearch"
              v-model="estimate.topContrNm"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              label="업체"
              readonly="readonly"
              append-inner-icon="mdi-magnify"
              @click:append-inner="openCompanySeach('COMP01')"
              v-model="estimate.compNm"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-select
              label="견적구분"
              :items="['A', 'B', 'C']"
              v-model="estimate.estimateDiv"
              :readonly="mode === MODAL_MODE.DETAIL"
              ></v-select>
          </v-col>
        </v-row>
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
            <a :href="`${REQUEST_URL}/common/download/${estimate.attachmentVO.fileId}`">
              {{ estimate.attachmentVO.orignFileName }}
            </a>
          </v-col>
        </v-row>
        <v-row>
          <v-col><br><hr><br></v-col>
        </v-row>
        <v-row>
          <v-col style="font-size: 20px">품목</v-col>
          <v-col style="text-align: right">
            <v-btn
              v-if="mode === MODAL_MODE.REG && validUtil.isNull(key)"
              @click="openProdModal()"
              color="green">품목 추가</v-btn>
            <br><br>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
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
              </tr>
              </thead>
              <tbody>
              <tr v-for="(prod, index) in prods" @click="openProdModal(prod, index)">
                <td>{{ prod.orderNo }}</td>
                <td>{{ prod.prodNm }}</td>
                <td>{{ prod.detailProdNm }}</td>
                <td>{{ prod.clientUnitPrice }}</td>
                <td>{{ prod.provProdPrice }}</td>
                <td>{{ prod.dcPer }}</td>
                <td>{{ prod.quantity }}</td>
              </tr>
              </tbody>
            </table>
          </v-col>
        </v-row>
        <v-row>
          <div class="modal-btn-list">
            <v-btn
              v-if="estimate.possibleConfirm"
              color="primary"
              @click="confirmEstimate"
            >
              확정
            </v-btn>
            <v-btn
              v-if="mode !== MODAL_MODE.DETAIL"
              color="green"
              @click="newEstimate"
            >저장</v-btn>
            　
            <v-btn
              v-if="mode !== MODAL_MODE.DETAIL"
              color="red"
              @click="deleteEstimate"
            >삭제</v-btn>
          </div>
        </v-row>
      </v-container>
    </div>
  </ModalLayout>

  <!-- 업체 담당자 -->
  <div v-if="prodModal" class="child-modal-overlay">
    <div class="child-modal">

      <div style="text-align: right">
        <div @click="closeProdModal" class="close"></div>
      </div>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.prodNm"
            label="품명"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.detailProdNm"
            label="세부품명"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.clientUnitPrice"
            label="소비자 단가"
          />
        </v-col>
        <v-col>
          <v-text-field
            v-model="prod.provProdPrice"
            label="공급 단가"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.dcPer"
            label="할인율"
          />
        </v-col>
        <v-col>
          <v-text-field
            v-model="prod.quantity"
            label="수량"
          />
        </v-col>
      </v-row>
      <div class="modal-btn-list">
        <v-btn
          color="green"
          @click="addProd"
          v-if="mode !== MODAL_MODE.DETAIL"
        >저장</v-btn>
        　
        <v-btn
          color="red"
          @click="deleteProd"
          v-if="mode !== MODAL_MODE.DETAIL"
        >삭제</v-btn>
      </div>
    </div>
  </div>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";
import TopContrSearch from "@/components/modal/search/TopContrSearch.vue";
import CompanySearch from "@/components/modal/search/CompanySearch.vue";

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
  beforeMount() {

    if(this.mode === MODAL_MODE.DETAIL){
      this.getEstimate();
      this.getProds();
    }

  },
  data() {
    return {
      compDiv: '',
      bTopContrSearch: false,
      bCompanySearch: false,

      mode: store.getters.getParams.mode,
      key: store.getters.getParams.key,

      file: '',
      estimate: {
        topContrId: '',
        topContrNm: '',

        compId: '',
        compNm: '',

        estimateDiv: '',

        attachmentVO:{
          fileId: '',
          orignFileName: '',
        },
        possibleConfirm: false
      },
      prod: {},
      prods: [],
      prodModal: false,
      prodIndex: 0,

      topContrNms: [],
      compNms: [],


    }
  },
  methods : {
    /* 원계약명 조회 */
    openTopContrSearch() {
      this.bTopContrSearch = !this.bTopContrSearch;
    },
    selectTopContr(obj) {
      this.estimate.topContrId = obj.id;
      this.estimate.topContrNm = obj.nm;
    },
    /* 업체 조회 */
    openCompanySeach(compDiv) {
      this.compDiv = compDiv;
      this.bCompanySearch = !this.bCompanySearch;
    },
    selectCompany(obj) {
      this.estimate.compNm = obj.nm;
      this.estimate.compId = obj.id;
    },

    async getTopContrNms() {
      this.topContrNms = await commonApi.topContrNms();
    },

    /* 업체명 조회 */
    async getCompNms() {
      this.compNms = await commonApi.compNms();
    },

    /* 파일 선택 */
    selectFile(file) {
      this.file = file.target.files[0];
    },

    /* ESTIMATE 상세조회 */
    async getEstimate() {
      this.estimate = await estimateApi.estimate(this.key);
      this.estimate.attachmentVO = validUtil.isNull(this.estimate.attachmentVO)??{};
    },

    /* PROD 목록조회 */
    async getProds() {
      this.prods = await prodApi.prods(this.key);
    },

    /* PROD 등록 모달 열기 */
    openProdModal(prod = {}, index = -1) {
      this.prodIndex = index;

      this.prod = {...prod};
      this.prodModal = true;
    },
    /* PROD 등록 모달 닫기 */
    closeProdModal() {
      this.prod = {};
      this.prodModal = false;
    },

    /* PROD 추가 */
    addProd() {
      if(!confirm("등록 하시겠습니까?")) return false;

      if(this.prodIndex > -1){ // 수정상태
        this.prods.splice(this.prodIndex, 1, this.prod);
      } else {
        this.prods.push(this.prod);
      }

      this.prod = {};

      this.closeProdModal();
    },
    /* PROD 삭제 */
    deleteProd() {
      if(!confirm("삭제 하시겠습니까?")) return false;

      this.prods.splice(this.prodIndex, 1);
      this.closeProdModal();
    },

    /* ESTIMATE 등록 */
    async newEstimate(){
      if(!confirm("등록 하시겠습니까?")) return false;
      this.estimate.prods = this.prods;

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

    modalClose(){
      this.$emit('update');
      this.$emit('close');
    },

  }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
  @import "@/assets/styles/customTable.css";

  .child-modal{
    width: 500px;
    height: 520px;
  }
</style>
