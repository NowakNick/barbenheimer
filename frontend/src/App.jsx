import React, { Component } from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

// Components
import Home from "./views/Home";
import Create from "./views/Create";
import Edit from "./views/Edit";
import NotFound from "./views/NotFound";
import { getMedia, getSingleMedia } from "./axios";

// Define Routs
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
    loader: getMedia,
  },
  {
    path: "/create",
    element: <Create />,
  },
  {
    path: "/edit/:id",
    element: <Edit />,
    loader: getSingleMedia,
  },
  {
    path: "*",
    element: <NotFound />,
  },
]);

class App extends Component {
  state = {};
  render() {
    return <RouterProvider router={router} />;
  }
}

export default App;
