<template>
  <div class="modal-overlay">

    <div class="modal">

      <div class="modal-header" style="text-align: right;">
        <div class="close" @click="closeModal"/>
      </div>

      <slot />

    </div>

  </div>
</template>

<script>
export default {
  name: "ModalLayout",
  methods: {
    closeModal(){
      this.$emit('close');
    }
  }
}
</script>

<style scoped>
  @import "@/assets/styles/modal.css";
</style>
