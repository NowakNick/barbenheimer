import React, { Component } from "react";

class Navbar extends Component {
  state = {};
  render() {
    return (
      <nav className="navbar bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="/">
            <img
              src="favicon.ico"
              alt="Logo"
              width="30"
              height="24"
              className="d-inline-block me-2"
            />
            <span className="align-text-top">Barbenheimer</span>
          </a>
        </div>
      </nav>
    );
  }
}

export default Navbar;
