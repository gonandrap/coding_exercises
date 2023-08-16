#pragma once

#include <string>
#include <fstream>

namespace datastructure
{
	class BigFile
	{
		size_t in_memory_size;
		size_t in_memory_chunk_size;			// equals in_memory_size + 1 to account for c-string \0
		size_t init_pos_file_in_memory;
		size_t end_pos_file_in_memory;
		std::string file_path;
		char* memory_chunk;
		std::fstream file_storage;
		size_t f_size;

	public:
		BigFile(std::string file_path, size_t in_memory_size);
		~BigFile();

		void load_in_memory(const size_t & from, const size_t & to);
		std::string in_memory(void) const;
		void set_memory(const std::string& value);
		void save_to_disk(void);
		size_t file_size(void);
	};
}
