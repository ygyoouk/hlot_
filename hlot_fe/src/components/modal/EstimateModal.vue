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
        <v-row>
          <v-file-input label="견적서" @change="selectFile" ></v-file-input>
        </v-row>
        <v-row>
          <v-col><hr><br></v-col>
        </v-row>
        <v-row>
          <v-col style="font-size: 20px">품목</v-col>
          <v-col style="text-align: right">
            <v-btn
              v-if="mode === MODAL_MODE.REG && validUtil.isNull(this.key)"
              @click="openProdModal()"
              color="green">품목 추가</v-btn>
            <br><br>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <table class="custom-table_mt">
              <thead>
              <tr>
                <th>순번</th>
                <th>품명</th>
                <th>세부품명</th>
                <th>소비자단가</th>
                <th>공급단가</th>
                <th>할인율</th>
                <th>수량</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="prod in prods" @click="openProdModal(prod)">
                <td>{{ prod.orderNo }}</td>
                <td>{{ prod.prodNm }}</td>
                <td>{{ prod.detailProdNm }}</td>
                <td>{{ prod.clientUnitPrice }}</td>
                <td>{{ prod.provProdPrice }}</td>
                <td>{{ prod.dcPer }}</td>
                <td>{{ prod.quantity }}</td>
              </tr>
              </tbody>
            </table>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </ModalLayout>

  <!-- 업체 담당자 -->
  <div v-if="prodModal" class="child-modal-overlay">
    <div class="child-modal">

      <div style="text-align: right">
        <div @click="closeProdModal" class="close"></div>
      </div>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.prodNm"
            label="품명"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.detailProdNm"
            label="세부품명"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.clientUnitPrice"
            label="소비자 단가"
          />
        </v-col>
        <v-col>
          <v-text-field
            v-model="prod.provProdPrice"
            label="공급 단가"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="prod.dcPer"
            label="할인율"
          />
        </v-col>
        <v-col>
          <v-text-field
            v-model="prod.quantity"
            label="수량"
          />
        </v-col>
      </v-row>
      <div class="modal-btn-list">
        <v-btn
          color="green"
          @click="addProd"
        >저장</v-btn>
        　
        <v-btn
          color="red"
          @click="deleteProd"
        >삭제</v-btn>
      </div>
    </div>
  </div>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";
</script>

<script>
import store from "@/store/store";
import commonApi from "@/api/common.js"
import estimateApi from "@/api/estimate.js";
import prodApi from "@/api/prod.js"
import {MODAL_MODE} from "@/util/config";



export default {
  name: "EstimateModal",
  beforeMount() {
    this.getTopContrNms();
    this.getCompNms();

    if(this.mode === MODAL_MODE.DETAIL){
      this.getEstimate();
      this.getProds();
    }

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
      prod: {},
      prods: [],
      prodModal: false,

      topContrNms: [],
      compNms: [],

      file: '',
    }
  },
  methods : {
    async getTopContrNms() {
      this.topContrNms = await commonApi.topContrNms();
    },

    async getCompNms() {
      this.compNms = await commonApi.compNms();
    },

    selectFile(file) {
      this.file = file.target.files[0];
    },


    async getEstimate() {
      this.estimate = await estimateApi.estimate(this.key);
    },

    async getProds() {
      this.prods = await prodApi.prods(this.key);
    },

    openProdModal(prod = {}) {
      this.prod = prod;
      this.prodModal = true;
    },
    closeProdModal() {
      this.prodModal = false;
    },

    addProd() {
      if(!confirm("등록 하시겠습니까?")) return false;

      this.prods.push(this.prod);
      this.prod = {};

      this.closeProdModal();
    },
    deleteProd() {
      if(!confirm("삭제 하시겠습니까?")) return false;

      let index = this.prods.findIndex(p => {
        return p.prodNm === this.prod.prodNm && p.detailProdNm === this.prod.detailProdNm;
      });

      this.prods.splice(index, 1);
      this.closeProdModal();
    }

  }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
  @import "@/assets/styles/customTable.css";

  .child-modal{
    width: 500px;
    height: 520px;
  }
</style>
