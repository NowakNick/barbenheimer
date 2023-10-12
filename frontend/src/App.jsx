import React, { Component } from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

// Components
import Home from "./views/Home";
import Create from "./views/Create";
import Edit from "./views/Edit";
import NotFound from "./views/NotFound";

// Define Routs
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/create",
    element: <Create />,
  },
  {
    path: "/edit/:id",
    element: <Edit />,
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
