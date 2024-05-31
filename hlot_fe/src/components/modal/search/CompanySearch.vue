<template>

  <SubModalLayout>

    <CompanyModal
      v-if="bCompanyModal"
      @close="bCompanyModal = false"
      @update="search"
      :compDiv="compDiv"
      :mode="MODAL_MODE.REG"
    />

    <div class="modal-title">
      업체 검색
    </div>

    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-select
        label="업체구분"
        density="compact"
        :items="compDivs"
        item-title="codeNm"
        item-value="code"
        readonly="readonly"
        v-model="searchCondition.compDiv"
      />

      <v-text-field
        label="업체명"
        density="compact"
        v-model="searchCondition.compNm"
      />

      <v-text-field
        label="사업자 등록번호"
        density="compact"
        v-model="searchCondition.compBussRegnum"
      />
      <v-btn
        color="primary"
        @click="search"
      >
        조회
      </v-btn>
      <v-btn
        color="primary"
        @click="searchCondition = {compDiv}"
      >
        초기화
      </v-btn>
      <v-btn
        color="green"
        @click="bCompanyModal = true"
      >
        업체등록
      </v-btn>
    </div>

    <div>
      <table class="custom-table_mt">
        <thead>
          <tr>
            <th>업체명</th>
            <th>업체구분</th>
            <th>사업자등록번호</th>
            <th>업체대표</th>
            <th>업체전화번호</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in companys" @click="select(c.compId, c.compNm, c.compDiv)">
            <td>{{ c.compNm }}</td>
            <td>{{ c.compDivNm }}</td>
            <td>{{ c.compBussRegnum }}</td>
            <td>{{ c.compCeoNm }}</td>
            <td>{{ c.compTel }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </SubModalLayout>

</template>


<script setup>
import SubModalLayout from "@/layouts/SubModalLayout.vue";
import CompanyModal from "@/components/modal/CompanyModal.vue";
import {MODAL_MODE} from "@/util/config";
</script>

<script>
import commonApi from "@/api/common.js";
import companyApi from "@/api/company.js";

  export default {
    props: ['compDiv'],

    async beforeMount() {

      /* 업체구분 공통코드 조회 */
      this.compDivs = await commonApi.cmmCodeComp("COMP");
      this.searchCondition.compDiv = this.compDiv;
    },

    data() {
      return {
        bCompanyModal: false,

        compDivs:[],
        companys:[],

        searchCondition:{
          compDiv: '',
          compNm: '',
          compBussRegnum: '',
        }
      }
    },

    methods : {
      async search() {
        this.companys = await companyApi.companys(this.searchCondition);
      },
      select(id, nm, div){
        this.$emit('select', {id, nm, div});
        this.$emit('close');
      }
    }
  }

</script>
<style scoped>
@import "@/assets/styles/customTable.css";
</style>
