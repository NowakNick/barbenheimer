import React, { Component } from "react";
import { NavLink } from "react-router-dom";

class Media extends Component {
  state = {};

  onDelete = (id) => {
    //TODO: send delete request and if return = true then refresh Data
    console.log("Button click -> Delete " + id);
  };

  render() {
    return (
      <div className="media-item col-12 col-md-6 col-lg-4 col-xl-4 col-xxl-3">
        <div className="card">
          <img
            src="/assets/test-image-1.png" //TODO: dynamic image
            className="card-img-top"
            alt={this.props.data.name}
          />
          <div className="card-body">
            <h5 className="card-title">{this.props.data.name}</h5>
            <p className="card-text">{this.props.data.type}</p>
            <div className="d-grid gap-2 d-flex justify-content-between">
              <NavLink
                className="btn btn-primary px-3"
                type="button"
                to={"/edit/" + this.props.data.id}
              >
                Edit
              </NavLink>
              <button
                onClick={() => this.onDelete(this.props.data.id)}
                className="btn btn-danger px-3"
                type="button"
              >
                <i className="bi bi-trash"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Media;
