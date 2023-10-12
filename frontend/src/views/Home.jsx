import React, { Component } from "react";
import Navbar from "../components/navbar";

class Home extends Component {
  state = {};
  render() {
    return (
      <div className="home">
        <Navbar />
        <h1>Home</h1>
      </div>
    );
  }
}

export default Home;
