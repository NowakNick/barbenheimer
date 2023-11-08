import axios from "axios";

const baseURL = process.env.REACT_APP_BACKEND_URL;

// Get all medias
export const getMedia = async () => {
  const res = await axios.get(baseURL + "/getMedia").catch((error) => {
    return {
      data: [],
    };
  });
  return res.data;
};

// Get single media
export const getSingleMedia = async ({ params }) => {
  const { id } = params;
  const res = await axios.get(baseURL + "/getSingleMedia/" + id);
  return res.data;
};

// add media
export const addMedia = async (formData) => {
  const res = await axios.post(baseURL + "/addMedia", formData);
  return res.data;
};

// Delete media
export const deleteMedia = async (id) => {
  return await axios.delete(baseURL + "/deleteMedia/" + id);
};

// Update media
export const updateMedia = async (id, formData) => {
  const res = await axios.patch(baseURL + "/updateMedia/" + id, formData);
  return res.data;
};
