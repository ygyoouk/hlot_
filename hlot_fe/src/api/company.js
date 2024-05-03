import axios from "axios";
import store from "@/store/store";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* COMPANY 상세조회 */
  company(id) {
    return axios.get(REQUEST_URL + `/api/company/${id}`).then(res => {
      return res.data.data;
    });
  },

  /* COMPANY 목록조회 */
  companys(){
    return axios.get(REQUEST_URL + '/api/companys').then(res => {
      return res.data.data;
    });
  },

  /* COMPANY 등록 or 수정 */
  newCompany(company) {
    company.registUserName = '유영국';
    return axios.post(REQUEST_URL + '/api/company', company).then(res => {
    });
  },

  /* COMPANY 삭제 */
  deleteCompany(id) {
    return axios.delete(REQUEST_URL + `/api/company/${id}`, id).then(res => {
    });
  },

  /* COMPANY_MANAGER 등록 OR 수정 */
  newCompanyManager(companyManager) {
    companyManager.registUserName = '유영국';
    return axios.post(REQUEST_URL + '/api/company/companyManager', companyManager).then(res => {
      // alert(res.data.message);
      return res.data;
    });
  },

  /* COMPANY_MANAGER 삭제 */
  deleteCompanyManager(id) {
    return axios.delete(REQUEST_URL + `/api/company/companyManager/${id}`).then(res => {
      return res.data;
    });
  }

}
