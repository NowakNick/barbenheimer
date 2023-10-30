import axios from "axios";
// TODO: add error handelers
// TODO: change paths if needed

const baseURL = process.env.REACT_APP_BACKEND_URL;
const dev = false; // false -> uses backend calls, true -> uses json-server calls

// Get all medias
export const getMedia = async () => {
  const res = await axios
    .get(baseURL + (dev ? "/media" : "/getMedia"))
    .catch((error) => {
      return {
        data: [],
      };
    });
  return res.data;
};

// Get single media
export const getSingleMedia = async ({ params }) => {
  const { id } = params;
  const res = await axios.get(
    baseURL + (dev ? "/media/" : "/getSingleMedia/") + id
  );
  return res.data;
};

// add media
export const addMedia = async (formData) => {
  const res = await axios.post(
    baseURL + (dev ? "/media" : "/addMedia"),
    formData
  );
  return res.data;
};

// Delete media
export const deleteMedia = async (id) => {
  return await axios.delete(baseURL + (dev ? "/media/" : "/deleteMedia/") + id);
};

// Update media
export const updateMedia = async (id, formData) => {
  const res = await axios.patch(
    baseURL + (dev ? "/media/" : "/updateMedia/") + id,
    formData
  );
  return res.data;
};
