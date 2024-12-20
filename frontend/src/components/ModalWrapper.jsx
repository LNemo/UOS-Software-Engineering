"use client";

const ModalWrapper = ({ isOpen, onClose, children, isTransparent }) => {
  if (!isOpen) return null;

  if(isTransparent)
    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50" onClick={onClose}>
      <div
        className="bg-white bg-opacity-0 p-6 relative max-w-[800px]"
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </div>
    </div>
  );

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50" onClick={onClose}>
      <div
        className="bg-white rounded shadow-lg p-6 relative max-w-[800px]"
        onClick={(e) => e.stopPropagation()}
      >
        <button
          className="absolute top-2 right-2 text-gray-500"
          onClick={onClose}
        >
          X
        </button>
        {children}
      </div>
    </div>
  );
};

export default ModalWrapper;