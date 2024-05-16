<template>
  <ModalLayout>
    <div class="modal-title">
      견적서 등록
    </div>

    <div class="modal-content">
      <v-container>
        <v-row>
          <v-col>
            <v-autocomplete
              label="원계약"
              :items="topContrNms"
              item-title="topContrNm"
              item-value="topContrId"
              v-model="estimate.topContrId"
              :readonly="mode === MODAL_MODE.DETAIL"
            ></v-autocomplete>
          </v-col>
          <v-col>
            <v-autocomplete
              label="업체"
              :items="compNms"
              item-title="compNm"
              item-value="compId"
              v-model="estimate.compId"
              :readonly="mode === MODAL_MODE.DETAIL"
            ></v-autocomplete>
          </v-col>
          <v-col>
            <v-select
              label="견적구분"
              :items="['A', 'B', 'C']"
              v-model="estimate.estimateDiv"
              ></v-select>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {MODAL_MODE} from "@/util/config";
</script>

<script>
import store from "@/store/store";
import commonApi from "@/api/common.js"

export default {
  name: "EstimateModal",
  beforeMount() {
    this.getTopContrNms();
    this.getCompNms();

  },
  data() {
    return {
      mode: store.getters.getParams.mode,
      key: store.getters.getParams.key,

      estimate: {
        topContrId: '',
        compId: '',
        estimateDiv: ''
      },

      topContrNms: [],
      compNms: [],
    }
  },
  methods : {
    async getTopContrNms() {
      this.topContrNms = await commonApi.topContrNms();
    },

    async getCompNms() {
      this.compNms = await commonApi.compNms();
    }
  }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
