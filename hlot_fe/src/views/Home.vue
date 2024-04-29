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
          <v-sheet>

          </v-sheet>
          <v-container>
            <v-textarea label="메모장" variant="outlined" rows="10"></v-textarea>
          </v-container>
        </v-col>
      </v-row>

    </v-container>
  </v-main>

  
  
  



</template>
<script>
export default {
  name : 'home',
  data(){
    return{
      datePicker: new Date(),
    }
  },
}
</script>
