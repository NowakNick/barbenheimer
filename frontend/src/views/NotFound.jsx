import React from "react";
import Navbar from "../components/navbar";
import { Link } from "react-router-dom";

export default function NotFound() {
  return (
    <div className="not-found">
      <Navbar />
      <h2>Sorry</h2>
      <p>That page cannot be found</p>
      <Link to="/">Back to the homepage...</Link>
    </div>
  );
}
