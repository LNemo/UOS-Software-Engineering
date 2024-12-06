"use client";

import CircleTag from "./CircleTag";

const CirclePreview = ({ circle }) => {
  const { name, image, tags } = club;

  return (
    <div className="flex items-center p-4 bg-white rounded-lg shadow">
      {/* Club Image */}
      <div className="flex-shrink-0 w-16 h-16">
        <img
          src={image}
          alt={name}
          className="w-full h-full object-cover rounded-lg"
        />
      </div>

      {/* Club Details */}
      <div className="ml-4 flex flex-col">
        {/* Club Name */}
        <h3 className="text-lg font-semibold text-gray-900">{name}</h3>

        {/* Club Tags */}
        <div className="mt-2 flex space-x-2">
          {tags.map((tag, index) => (
            <CircleTag key={index} tag={tag} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default CirclePreview;