import React, { Component } from "react";
import Media from "./media";

class MediaList extends Component {
  state = {};
  render() {
    if (this.props.data.length !== 0) {
      return (
        <div className="media-container row mt-0 g-4 px-4">
          {this.props.data.map((item) => (
            <Media key={item.id} data={item} />
          ))}
        </div>
      );
    } else {
      return (
        <div className="media-container row mt-0 g-4 px-4">
          <div className="media-item col-12 col-md-6 col-lg-4 col-xl-4 col-xxl-3">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">No media uploaded!</h5>
                <p className="card-text">To add media select upload button.</p>
              </div>
            </div>
          </div>
        </div>
      );
    }
  }
}

export default MediaList;
