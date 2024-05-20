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
import store from "@/store/store";

export default {
  name: "ModalLayout",
  computed: {
    store() {
      return store
    }
  },
  methods: {
    closeModal(){
      this.$emit('close');
      store.commit('toggleModal');
    }
  }
}
</script>

<style scoped>
  @import "@/assets/styles/modal.css";
</style>
