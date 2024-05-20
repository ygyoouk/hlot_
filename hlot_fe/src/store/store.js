import { createStore } from 'vuex'

const store = createStore({
  state: {
    isOpenModal : false,
    params: {
      key : '',
      mode : '',
    },
  },

  getters: {
    isOpenModal(state){
      return state.isOpenModal;
    },

    getParams(state) {
      return state.params;
    },

  },

  mutations: {
    /* params = {key : '', mode : ''} */
    toggleModal(state, params = {}){
      state.params = params;
      state.isOpenModal = !state.isOpenModal;
    },

    setModalParams(state, params ={}) {
      state.params = params;
    }
  }
})

export default store;
