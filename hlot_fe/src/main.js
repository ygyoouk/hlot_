import App from './App.vue'
import router from '@/router/index.js'
import vuetify from "@/plugins/vuetify.js";
import store from "@/store/store.js"
import axios from "axios";
import {createApp} from 'vue'

axios.interceptors.request.use(
  request => {
    request.headers["Authorization"] = "Bearer " + store.getters.getUser.token;
    return request;
  }
)

createApp(App)
  .use(router)
  .use(vuetify)
  .use(store)
  .mount('#app');

