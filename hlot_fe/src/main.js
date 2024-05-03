import App from './App.vue'
import router from '@/router/index.js'
import vuetify from "@/plugins/vuetify.js";
import store from "@/store/store.js"
import { createApp } from 'vue'

createApp(App)
  .use(router)
  .use(vuetify)
  .use(store)
  .mount('#app');

