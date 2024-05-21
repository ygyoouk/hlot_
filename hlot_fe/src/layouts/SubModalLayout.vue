<template>
  <div class="child-modal-overlay">
    <div class="child-modal">
      <div class="modal-header" style="text-align: right;">
        <div class="close" @click="closeModal"/>
      </div>

      <slot />

    </div>
  </div>
</template>

<script>
export default {
  name: "SubModalLayout",
  methods: {
    closeModal(){
      this.$emit('close');
    }
  }
}
</script>

<style scoped>
@import "@/assets/styles/modal.css";
@import "@/assets/styles/customTable.css";

.child-modal{
  width: 850px;
  height: 700px;
}
</style>
