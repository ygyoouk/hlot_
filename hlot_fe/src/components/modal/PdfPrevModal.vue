<template>

  <ModalLayout
    @close="modalClose"
  >
    <div style="display: flex; justify-content: center">
      <VuePDF :pdf="pdf" :scale="1.5"></VuePDF>
    </div>
  </ModalLayout>
</template>

<script>
import ModalLayout from "@/layouts/ModalLayout.vue";
import { VuePDF, usePDF } from '@tato30/vue-pdf'

export default {
  name: "PdfPrevModal",
  components: {ModalLayout, VuePDF},
  props: ['pdfFileLink'],
  beforeMount() {
    this.pdf = usePDF(this.$props.pdfFileLink).pdf;
  },
  data() {
    return {
      pdf: '',
    }
  },
  methods: {
    modalClose() {
      this.$emit('close');
    }
  }

}
</script>

<style scoped>
  @import "@/assets/styles/modal.css";
</style>
