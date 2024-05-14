import axios from "axios";
import topContr from "@/api/project.js";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;


export default {

  //CONTR 조회
  contrs(){
    return axios.get(REQUEST_URL + '/api/contrs').then(res => {
        console.log(res.data.data);
      return res.data.data;
    });
  },


  /**
   * 계약 파일 저장
   */
  saveContrFile(formData){
    
    return axios.post(REQUEST_URL + '/common/pdfUpload',formData,{header:{'Content-Type' : 'multipart/form-data'}}).then(res=>{
      return res;
    });
  }

}
