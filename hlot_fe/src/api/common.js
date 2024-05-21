import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* TOP_CONTR ID, 명 조회 */
  topContrNms(topContrId){
    return axios.get(REQUEST_URL + '/common/topContrNms/'+topContrId).then(res => {
      console.log(res.data.data);
      return res.data.data;
    });
  },

  /* COMP ID, 명 조회 */
  compNms(compId){
    return axios.get(REQUEST_URL + '/common/compNms/'+compId).then(res => {
      console.log(res.data.data);
      return res.data.data;

    });
  },

  /* 업체구분 조회 */
  cmmCodeComp(codeNm) {
    return axios.get(REQUEST_URL + `/common/code/${codeNm}`).then(res => {
      console.log(res);
      return res.data.data;
    })
  }
}
