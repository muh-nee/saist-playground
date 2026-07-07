package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

type UserDB struct {
	DB *gorm.DB
}

func (h *UserDB) GetUser(c *gin.Context) {
	id := c.Param("id")
	var user struct {
		Name string
	}
	if err := h.DB.Raw("SELECT name FROM users WHERE id = ?", id).Scan(&user).Error; err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, user)
}
