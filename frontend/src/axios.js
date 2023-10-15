import axios from "axios";
// TODO: add error handelers
// TODO: change paths if needed

const baseURL = process.env.REACT_APP_BACKEND_URL;

// Get all medias
export const getMedia = async () => {
  const res = await axios.get(baseURL + "/media");
  return res.data;
};

// Get single media
export const getSingleMedia = async ({ params }) => {
  const { id } = params;
  const res = await axios.get(baseURL + "/media/" + id);
  return res.data;
};

// add media
export const addMedia = async (formData) => {
  const res = await axios.post(baseURL + "/media", formData);
  return res.data;
};

// Delete media
export const deleteMedia = async (id) => {
  const res = await axios.delete(baseURL + "/media/" + id);
  return res.data;
};

// Update media
export const updateMedia = async (id, formData) => {
  const res = await axios.patch(baseURL + "/media/" + id, formData);
  return res.data;
};
