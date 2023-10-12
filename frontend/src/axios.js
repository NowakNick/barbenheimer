import axios from "axios";

const baseURL = "http://localhost:4000";

// Get all medias
export const getMedia = async () => {
  const res = await axios.get(baseURL + "/data");
  return res.data;
};

// Get single media
export const getSingleMedia = async ({ params }) => {
  const { id } = params;
  const res = await axios.get(baseURL + "/singleData/" + id);
  return res.data;
};

// add media
export const addMedia = async () => {
  // TODO: implement add
};

// Delete media
export const deleteMedia = async () => {
  // TODO: implement delete
  return null;
};
