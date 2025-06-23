import React from 'react';
import { Typography, Box } from '@mui/material';

const Home: React.FC = () => {
  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        Welcome to OSSori
      </Typography>
      <Typography variant="body1">
        This is the home page of your application. You can start building your content here.
      </Typography>
    </Box>
  );
};

export default Home; 