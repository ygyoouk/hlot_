<template>
  <SubModalLayout>
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
        v-model="searchCondition.compBussRegNum"
      />
      <v-btn
        color="primary"
        @click="search"
      >
        조회
      </v-btn>
    </div>

  </SubModalLayout>

</template>


<script setup>
import SubModalLayout from "@/layouts/SubModalLayout.vue";
</script>

<script>
import commonApi from "@/api/common.js";
import companyApi from "@/api/company.js";

  export default {
    async beforeMount() {
      this.compDivs = await commonApi.cmmCodeComp("COMP");
    },

    data() {
      return {
        compDivs:[],
        searchCondition:{
          compDiv: '',
          compNm: '',
          compBussRegNum: '',
        }
      }
    },

    methods : {
      async search() {
        await companyApi.companys(this.searchCondition);
      }
    }
  }

</script>
<style scoped>
</style>
