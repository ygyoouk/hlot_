import axios from "axios";
import {createRouterMatcher as Promise} from "vue-router";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* ESTIMATE 목록조회 */
  estimates(params){
    return axios.get(REQUEST_URL + '/api/estimates', {params}).then(res => {
      return res.data.data;
    });
  },

  /* ESTIMATE 상세조회 */
  estimate(id) {
    return axios.get(REQUEST_URL + `/api/estimate/${id}`).then(res => {
      return res.data.data;
    });
  },

  /* ESTIMATE 등록 */
  newEstimate(data) {
    const config = {
      header: {'Content-Type': 'multipart/form-data'}
    };
    return axios.post(REQUEST_URL + `/api/estimate`, data, config).then(res => {
      return res.data.data;
    })
  },

  /* ESTIMATE 확정 */
  confirmEstimate(id) {
    return axios.get(REQUEST_URL + `/api/estimate/confirm/${id}`, id).then(res => {
      return res.data;
    });
  },

  /* ESTIMATE 삭제 */
  deleteEstimate(id) {
    return axios.get(REQUEST_URL + `/api/estimate/delete/${id}`, id).then(res => {
      return res.data;
    })
  }

}
