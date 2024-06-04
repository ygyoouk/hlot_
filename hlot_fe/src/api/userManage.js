import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* USER 목록조회 */
  users(params) {
    return axios.get(REQUEST_URL + `/api/users`, {params}).then(res => {
      return res.data.data;
    });
  },
  /* USER 단건조회 */
  user(id) {
    return axios.get(REQUEST_URL + `/api/user/${id}`).then(res => {
      return res.data.data;
    })
  },
  /* USER 등록 */
  newUser(user) {
    return axios.post(REQUEST_URL + '/api/user', user).then(res => {
    });
  },
  /* USER 삭제 */
  deleteUser(id) {
    return axios.delete(REQUEST_URL + `/api/user/${id}`).then(res => {

    })
  }

}
