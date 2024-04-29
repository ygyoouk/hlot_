<template>
  <title>MT Data</title>
  <v-app id="inspire">
    <Header class="header-container_mt"/>
    <div class="main-container_mt">
      <RouterView />
    </div>
  </v-app>

</template>

<script>
import Header from '@/components/common/Header.vue';
import store from "@/store/store";
  export default {
    computed: {
      store() {
        return store
      }
    },
    components: {Header},
  }
</script>

<style>
  @import "@/assets/styles/common.css";
</style>
