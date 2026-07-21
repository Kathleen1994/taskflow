import api from "./api";

export async function register(username, password) {
  const response = await api.post("/auth/register", null, {
    params: {
      username,
      password,
    },
  });

  return response.data;
}
