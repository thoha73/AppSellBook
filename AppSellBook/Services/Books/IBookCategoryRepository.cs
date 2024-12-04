﻿using AppSellBook.Entities;

namespace AppSellBook.Services.Books
{
    public interface IBookCategoryRepository
    {
        Task<BookCategory> CreateBookCategory(BookCategory bookCategory);
        Task<BookCategory> UpdateBookCategory(BookCategory bookCategory);
    }
}