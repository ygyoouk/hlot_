import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  // TOP_CONTR 조회
  contrs(){
    return axios.get(REQUEST_URL + '/api/contrs').then(res => {
        console.log(res.data.data);
      return res.data.data;
    });
  },

}
