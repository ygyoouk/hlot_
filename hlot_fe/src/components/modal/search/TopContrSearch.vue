<template>

  <CompanySearch
    v-if="bCompanySearch"
    @close="bCompanySearch = !bCompanySearch"
    style="z-index: 3"
  />

  <SubModalLayout
    @close="this.$emit('close')"
  >
    <div class="modal-title">
      원계약 검색
    </div>
    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-text-field
        label="원계약명"
        density="compact"
      />
      <v-select
        label="계약구분(품명)"
        density="compact"
      />
      <v-text-field
        label="발주처"
        readonly="readonly"
        append-inner-icon="mdi-magnify"
        @click:append-inner="searchCompany"
        density="compact"
      ></v-text-field>
    </div>
    <div class="d-flex flex-wrap ga-3 mt-0">
      <v-text-field
        label="계약기간"
        density="compact"
      />
      <v-text-field
        label="수요기관"
        readonly="readonly"
        append-inner-icon="mdi-magnify"
        @click:append-inner="searchCompany"
        density="compact"
      ></v-text-field>
      <v-btn
        color="primary"
      >
        조회
      </v-btn>
    </div>
  </SubModalLayout>
</template>
<script setup>
import SubModalLayout from "@/layouts/SubModalLayout.vue";
import CompanySearch from "@/components/modal/search/CompanySearch.vue";
</script>
<script>
export default {
  data() {
    return {
      bCompanySearch: false,
    }
  },
  methods : {
    searchCompany() {
      this.bCompanySearch = !this.bCompanySearch;
    }
  }
}
</script>

<style scoped>
@import "@/assets/styles/common.css";

</style>
