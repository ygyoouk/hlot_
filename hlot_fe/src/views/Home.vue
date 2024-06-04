<template>
  <v-main class="bg-grey-lighten-2">
    <v-container>
      <v-row>
        <v-col>
          <v-sheet
            min-height="90vh"
            rounded="lg"
          >
            <!-- 메인화면 가운데 -->
            <v-date-picker v-model="datePicker"></v-date-picker>
          </v-sheet>
        </v-col>
        <v-col>
          <div>최근 원계약</div>   
          <br>
            <table class="custom-table_mt" v-if="topContr != ''">
              <thead>
                <tr>
                  <th>원계약명</th>
                  <th>원계약구분</th>
                  <th>등록일자</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="c in topContr">
                  <td>{{ c.topContrNm }}</td>
                  <td>{{ c.topContrDiv }}</td>
                  <td>{{ c.registDate }}</td>
                </tr>
              </tbody>
            </table>
            <br>
            <div>최근 견적서</div>   
            <br>
            <table class="custom-table_mt" v-if="estimate != ''">
              <thead>
                <tr>
                  <th>원계약명</th>
                  <th>업체명</th>
                  <th>견적구분</th>
                  <th>등록일자</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="e in estimate">
                  <td>{{ e.topContrNm }}</td>
                  <td>{{ e.compNm }}</td>
                  <td>{{ e.estimateDivNm }}</td>
                  <td>{{ e.registDate }}</td>
                </tr>
              </tbody>
            </table>
        </v-col>
      </v-row>

    </v-container>
  </v-main>
</template>

<script setup>
import commonApi from "@/api/common";
</script>

<script>
export default {
  name : 'home',

  async beforeMount(){

    this.userName = JSON.parse(localStorage.getItem("userName")).value

    this.topContr = await commonApi.selectLatestTopContrInfo(JSON.parse(localStorage.getItem("userName")).value);
    this.estimate = await commonApi.selectLatestEstimateInfo(JSON.parse(localStorage.getItem("userName")).value);

    console.log(this.estimate);
  },

  data(){
    return{
      datePicker: new Date(),
      userName : '',
      topContr : [],
      estimate : [],
    }
  },
}
</script>
<style>
  @import "@/assets/styles/customTable.css";
</style>