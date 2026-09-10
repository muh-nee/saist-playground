async function updateUser(req, User) {
  const updateData = {
    email: req.body.email,
    firstName: req.body.firstName,
    lastName: req.body.lastName,
  };

  return User.update(updateData, { where: { id: req.params.id } });
}

module.exports = { updateUser };
