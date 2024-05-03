import { createStore } from 'vuex'

const store = createStore({
  state: {
    isOpenModal : false,
    params: {
      key : '',
      mode : '',
    },
    user: {
      isLogin: false,
      userId: '',
      userNm: '관리자',
    }
  },

  getters: {
    isOpenModal(state){
      return state.isOpenModal;
    },

    getParams(state) {
      return state.params;
    },

    getUser(state){
      return state.user;
    }
  },

  mutations: {
    /* params = {key : '', mode : ''} */
    toggleModal(state, params = {}){
      state.params = params;
      state.isOpenModal = !state.isOpenModal;
    },

    setUser(state, params = {}) {
      state.user = params;
    },
  }
})

export default store;
