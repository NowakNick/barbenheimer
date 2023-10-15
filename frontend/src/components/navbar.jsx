import React, { Component } from "react";
import { NavLink } from "react-router-dom";

class Navbar extends Component {
  state = {};
  render() {
    return (
      <nav className="navbar bg-body-tertiary">
        <div className="container-fluid">
          <NavLink className="navbar-brand" to="/">
            <img
              src="favicon.ico"
              alt="Logo"
              width="30"
              height="24"
              className="d-inline-block me-2"
            />
            <span className="align-text-top">Barbenheimer</span>
          </NavLink>
        </div>
      </nav>
    );
  }
}

export default Navbar;
