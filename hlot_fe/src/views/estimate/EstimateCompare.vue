
<template>
  <v-card class="table-container_mt">
    <div class="table-title_mt">
      견적서 비교
    </div>

    <div class="d-flex flex-wrap ga-3 mb-0">
      <VuePDF :pdf="pdf" />
      <VuePDF :pdf="pdf" />
      <VuePDF :pdf="pdf" />
      <VuePDF :pdf="pdf" />
      <VuePDF :pdf="pdf" /><VuePDF :pdf="pdf" />
      <VuePDF :pdf="pdf" />

    </div>
  </v-card>
</template>

<script setup>
import { VuePDF, usePDF } from '@tato30/vue-pdf'

const { pdf } = usePDF('http://localhost:8081/common/download/63ec9b76-da75-4f54-b6b0-b80455394265_20240522');
</script>

<style scoped>

</style>
