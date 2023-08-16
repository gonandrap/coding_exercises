
#include "big_file.h"
#include <cassert>
#include <iostream>

using namespace datastructure;

BigFile::BigFile(std::string file_path, size_t in_memory_size) : in_memory_size(in_memory_size), in_memory_chunk_size(in_memory_size+1), file_path(file_path), memory_chunk(nullptr), init_pos_file_in_memory(0), end_pos_file_in_memory(0), file_storage(this->file_path)
{
	this->memory_chunk = new char[this->in_memory_chunk_size];
	memset(this->memory_chunk, 0, this->in_memory_chunk_size);

	if (!this->file_storage.is_open())
	{
		std::cout << "ERROR opening file " << this->file_path << std::endl;
	}

	// file is opened from the beginning
	std::streampos begin, end;
	begin = this->file_storage.tellg();
	this->file_storage.seekg(0, std::ios::end);
	end = this->file_storage.tellg();
	this->f_size = end - begin;
}

BigFile::~BigFile()
{
	delete(this->memory_chunk);
	this->file_storage.close();
}

void BigFile::load_in_memory(const size_t & from, const size_t & to)
{
	memset(this->memory_chunk, 0, this->in_memory_size);
	assert(to - from <= this->in_memory_size);
	
	// keep the limits so we know where to persist it back 
	init_pos_file_in_memory = from;
	end_pos_file_in_memory = to;

	this->file_storage.seekg(from, std::ios::beg);		// position at location "from" from the beginning of the file
	this->file_storage.read(this->memory_chunk, to - from);
}

std::string BigFile::in_memory(void) const
{
	return std::string(this->memory_chunk, this->in_memory_chunk_size);
}

void BigFile::set_memory(const std::string& value)
{
	std::copy(value.begin(), value.end(), this->memory_chunk);
}

void BigFile::save_to_disk(void)
{
	this->file_storage.seekp(this->init_pos_file_in_memory, std::ios::beg);
	this->file_storage.write(this->memory_chunk, this->end_pos_file_in_memory - this->init_pos_file_in_memory);
}

size_t BigFile::file_size(void)
{
	return this->f_size;
}